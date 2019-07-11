package uz.gigalab.remonlineimport.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import uz.gigalab.remonlineimport.domain.*;
import uz.gigalab.remonlineimport.dto.RemOnlineData;
import uz.gigalab.remonlineimport.repository.*;
import uz.gigalab.remonlineimport.service.utils.ConvertDateUtil;

import java.util.List;

import static uz.gigalab.remonlineimport.service.utils.ConvertDateUtil.convertToLocalDate;
import static uz.gigalab.remonlineimport.service.utils.ConvertDateUtil.convertToLocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class ImportService {

    private final OrdersRepository          ordersRepository;
    private final ContactsRepository        contactsRepository;
    private final ModelsRepository          modelsRepository;
    private final ModelsGadgetsRepository   gadgetsRepository;
    private final ModelsBrandsRepository    brandsRepository;
    private final ServicesRepository        servicesRepository;

    private final UsersServices             usersServices;


    @Transactional
    public void saveData(List<RemOnlineData> list) {
        Long servicesId = getServicesId();
        for (RemOnlineData data : list) {
            Long id = Long.parseLong(data.getOrder().replaceAll("[^0-9]", ""));
            Orders.OrdersBuilder builder = Orders.builder()
                    .id(id)
                    .contacts(getContact(data))
                    .models(getModel(data))
                    .servicesId(servicesId)

                    .createUid(getUsersId(data.getManager()))
                    .createDate(convertToLocalDateTime(data.getTakenDate()))

                    .priority(2)
                    .isService(false)
                    .isWarranty(false)
                    .isClose(true)

                    .oriented(!StringUtils.isEmpty(data.getApproximatePrice()) ? data.getApproximatePrice() : 0)
                    .imei(data.getSerialNumber())

                    // Установка статусов
                    .clientSid(7)
                    .acceptanceSid(10)
                    .detailSid(17)
                    .repairSid(22)

                    //
                    .acceptanceUid(getUsersId(data.getTakeUser()))
                    .acceptanceDate(convertToLocalDateTime(data.getTakenDate()))

                    .deliveryUid(getUsersId(data.getExtraditeUser()))
                    .deliveryDate(convertToLocalDateTime(data.getIssuedDate()))

                    // Сумма услуг
                    .sumPrice((double) data.getAmount())
                    .sumDiscount(0.0)
                    .sumTotal((double) data.getAmount())
                    // Сумма расходов
                    .sumExpense((double) data.getCostPrice())
                    // Сумма оплаты
                    .sumPaid((double) data.getPaid())
                    // Сумма прибыли
                    .sumIncome((double) data.getPaid() - (double) data.getCostPrice())

                    .guaranteeRid(84);

            //region Примечание
            if (!StringUtils.isEmpty(data.getBug())) {
                builder.bug(new Texts(data.getBug()));
            }

            StringBuilder noteBuild = new StringBuilder();
            noteBuild.append("Перенос данных их RemOnline");

            if (!StringUtils.isEmpty(data.getExecutedWork())) {
                noteBuild.append("\nВыполненные работы: ")
                        .append(data.getExecutedWork());
            }
            if (!StringUtils.isEmpty(data.getSpareParts())) {
                noteBuild.append("\nУстановленные запчасти: ")
                        .append(data.getSpareParts());
            }
            if (!StringUtils.isEmpty(data.getNote())) {
                noteBuild.append("\nЗаметки исполнителя: ")
                        .append(data.getNote());
            }
            if (!StringUtils.isEmpty(data.getReceiverNotes())) {
                noteBuild.append("\nЗаметки приемщика: ")
                        .append(data.getReceiverNotes());
            }
            if (!StringUtils.isEmpty(data.getPicking())) {
                noteBuild.append("\nКомплектация: ")
                        .append(data.getPicking());
            }
            if (!StringUtils.isEmpty(data.getAppearance())) {
                noteBuild.append("\nВнешний вид: ")
                        .append(data.getAppearance());
            }
            //endregion


            // Тип заказа
            if ("По гарантии".contains(data.getType())) {
                builder.isWarranty(true);
            }

            //region Статус выдачи
            if ("Выдан без ремонта".contains(data.getStatus())) {
                builder.globalSid(28)
                        .deliverySid(28)
                        .deliveryType(2);
            } else if ("Выдан".contains(data.getStatus())) {
                builder.globalSid(27)
                        .deliverySid(27)
                        .deliveryType(1);
            }
            //endregion

            builder.note(new Texts(noteBuild.toString()));

            // build
            Orders orders = builder.build();

            // Выдача без ремонта
            if (28 == orders.getGlobalSid()) {
                OrdersDenials ordersDenials = OrdersDenials.builder()
                        .ordersId(orders.getId())
                        .orders(orders)
                        .denialsRid(70)
                        .createUid(orders.getDeliveryUid())
                        .createDate(orders.getDeliveryDate())
                        .note(new Texts("Импорт из RemOnline"))
                        .build();
                orders.setOrdersDenials(ordersDenials);
            }

            // Внесени оплаты
            if (orders.getSumPaid() > 0) {
                OrdersFinances ordersFinances = OrdersFinances.builder()
                        .orders(orders)
                        .paymentDate(convertToLocalDate(data.getIssuedDate()))
                        .amount(orders.getSumPaid())
                        .createUid(orders.getDeliveryUid())
                        .createDate(orders.getDeliveryDate())
                        .updateUid(orders.getDeliveryUid())
                        .updateDate(orders.getDeliveryDate())
                        .build();

                orders.getOrdersFinances().add(ordersFinances);
            }

            // Внесени расходов
            if (orders.getSumExpense() > 0) {
                OrdersCosts ordersCosts = OrdersCosts.builder()
                        .orders(orders)
                        .financesItemsId(19)
                        .paymentDate(convertToLocalDate(data.getIssuedDate()))
                        .amount(orders.getSumExpense())

                        .createUid(orders.getDeliveryUid())
                        .createDate(orders.getDeliveryDate())
                        .updateUid(orders.getDeliveryUid())
                        .updateDate(orders.getDeliveryDate())
                        .note(new Texts("Себестоимость из RemOnline"))
                        .build();

                orders.getOrdersCosts().add(ordersCosts);
            }

            ordersRepository.save(orders);
        }
    }


    private Contacts getContact(RemOnlineData data) {
        Contacts contact = new Contacts();
        contact.setName(data.getClientName());

        contact.setCreateUsersId(1L);
        contact.setDateCreate(ConvertDateUtil.convertToLocalDateTime(data.getTakenDate()));
        contact.setDateUpdate(ConvertDateUtil.convertToLocalDateTime(data.getTakenDate()));

        if (!StringUtils.isEmpty(data.getClientPhone())) {
            contact.addRosters(ContactsRosters.createPhone(data.getClientPhone()));
        }
        if (!StringUtils.isEmpty(data.getClientEmail())) {
            contact.addRosters(ContactsRosters.createEmail(data.getClientEmail()));
        }
        if (!StringUtils.isEmpty(data.getClientAddress())) {
            contact.addRosters(ContactsRosters.createAddress(data.getClientAddress()));
        }

        contactsRepository.save(contact);
        return contact;
    }
    private Models getModel(RemOnlineData data) {
        ModelsGadgets gadgets = gadgetsRepository.findByName(data.getGadget())
                .orElseGet(() -> {
                    ModelsGadgets entity = new ModelsGadgets(data.getGadget());
                    gadgetsRepository.save(entity);
                    return entity;
                });

        ModelsBrands brands = brandsRepository.findByName(data.getBrand())
                .orElseGet(() -> {
                    ModelsBrands entity = new ModelsBrands(data.getBrand());
                    brandsRepository.save(entity);
                    return entity;
                });

        Models models = modelsRepository.find(data.getModel(), gadgets.getId(), brands.getId())
                .orElseGet(() -> {
                    Models entity = new Models(data.getModel(), gadgets, brands);
                    modelsRepository.save(entity);
                    return entity;
                });

        return models;
    }
    private Long getServicesId() {
        Services services = servicesRepository.findFirstByStatusAndType(1, 2);
        return services.getId();
    }
    private Long getUsersId(String fullName) {
        return usersServices.getUsersId(fullName);
    }
}


















