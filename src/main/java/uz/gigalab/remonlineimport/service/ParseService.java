package uz.gigalab.remonlineimport.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import uz.gigalab.remonlineimport.dto.RemOnlineData;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class ParseService {

    public List<RemOnlineData> readData(Resource resourceFile) {
        HSSFWorkbook wb = readWorkbook(resourceFile);
        if (wb == null) {
            log.error("Wb is null");
            System.exit(-1);
        }

        List<RemOnlineData> rl = new ArrayList<>();
        HSSFSheet sheet = wb.getSheetAt(0);

        sheet.forEach(row -> {
            RemOnlineData rd = parseRow(row);
            rl.add(rd);
        });

        return rl;
    }

    private HSSFWorkbook readWorkbook(Resource resourceFile) {
        try {
            File file = resourceFile.getFile();
            POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(file));
            return new HSSFWorkbook(fs);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    private RemOnlineData parseRow(Row r) {
        RemOnlineData data =  RemOnlineData.builder()
                // Принят
                .takenDate(getDate(r.getCell(0)))
                // Принял
                .takeUser(getString(r.getCell(1)))
                // № заказа
                .order(getString(r.getCell(2)))
                // Тип заказа
                .type(getString(r.getCell(3)))
                // Статус
                .status(getString(r.getCell(4)))
                // Имя
                .clientName(getString(r.getCell(5)))
                // Телефон
                .clientPhone(getString(r.getCell(6)))
                // Адрес
                .clientAddress(getString(r.getCell(7)))
                // Email
                .clientEmail(getString(r.getCell(8)))
                // Оплачено
                .paid(getNumber(r.getCell(10)))
                // Готов
                .readyDate(getDate(r.getCell(11)))
                // Менеджер
                .manager(getString(r.getCell(12)))
                // Выдан
                .issuedDate(getDate(r.getCell(14)))
                // Выдал
                .extraditeUser(getString(r.getCell(15)))
                // Себестоимость
                .costPrice(getNumber(r.getCell(16)))
                // Сумма
                .amount(getNumber(r.getCell(17)))
                // Заметки исполнителя
                .note(getString(r.getCell(18)))
                // Выполненные работы
                .executedWork(getString(r.getCell(19)))
                // Установленные запчасти
                .spareParts(getString(r.getCell(20)))
                // Вердикт / рекомендации клиенту
                // Мастерская

                // Комплектация
                .picking(getString(r.getCell(23)))
                // Неисправность
                .bug(getString(r.getCell(24)))
                // Модель
                .model(getString(r.getCell(25)))
                // Бренд
                .brand(getString(r.getCell(26)))
                // Тип устройства
                .gadget(getString(r.getCell(27)))
                // Серийный номер
                .serialNumber(getString(r.getCell(28)))
                // Время вызова мастера
                // Срочно
                // Заметки приемщика
                .receiverNotes(getString(r.getCell(31)))
                // Внешний вид
                .appearance(getString(r.getCell(32)))
                // Ориентировочная цена
                .approximatePrice(getNumber(r.getCell(33)))

                .build();

        if (StringUtils.isEmpty(data.getBrand())) {
            data.setBrand("n/a");
        }
        if (StringUtils.isEmpty(data.getModel())) {
            data.setModel("n/a");
        }
        if (StringUtils.isEmpty(data.getGadget())) {
            data.setGadget("n/a");
        }
        return data;
    }

    private Date getDate(Cell cell) {
        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            if (HSSFDateUtil.isCellDateFormatted(cell)) {
                double dv = cell.getNumericCellValue();
                return HSSFDateUtil.getJavaDate(dv);
            }
        }

        throw new RuntimeException("Ошибка получения даты");
    }
    private String getString(Cell cell) {
        if (cell.getCellType() == Cell.CELL_TYPE_BLANK) {
            return null;
        }

        if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
            return cell.getStringCellValue();
        }

        throw new RuntimeException("Ошибка получения строки");
    }
    private Long getNumber(Cell cell) {
        if (cell.getCellType() == Cell.CELL_TYPE_BLANK) {
            return null;
        }
        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            return (long) cell.getNumericCellValue();
        }

        if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
            return Long.parseLong(cell.getStringCellValue());
        }

        throw new RuntimeException("Ошибка получения числа: " + cell.getCellType());
    }
}
