package uz.gigalab.remonlineimport.dto;

import lombok.*;

import java.util.Date;

@Data
@Builder()
@NoArgsConstructor
@AllArgsConstructor
public class RemOnlineData {

    private Date takenDate;
    private String takeUser;
    private String order;
    private String type;
    private String status;

    private String clientName;
    private String clientPhone;
    private String clientAddress;
    private String clientEmail;

    private Date readyDate;

    private String manager;

    private Date issuedDate;
    private String extraditeUser;

    private Long paid;
    private Long costPrice;
    private Long amount;

    // Заметки исполнителя
    private String note;
    // Выполненные работы
    private String executedWork;
    // Установленные запчасти
    private String spareParts;
    // Комплектация
    private String picking;
    // Неисправность
    private String bug;

    // Модель
    private String model;
    private String brand;
    private String gadget;
    private String serialNumber;

    // Заметки приемщика
    private String receiverNotes;
    // Внешний вид
    private String appearance;

    // Ориентировочная цена
    private Long approximatePrice;
}
