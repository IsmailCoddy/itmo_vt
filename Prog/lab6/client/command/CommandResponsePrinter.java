package client.command;

import common.dto.CollectionInfo;
import common.dto.CommandResponse;
import model.Flat;

import java.util.List;


public class CommandResponsePrinter {


    public void print(CommandResponse response) {
        if (response == null) {
            System.out.println("Сервер вернул пустой ответ.");
            return;
        }

        if (!response.isSuccess()) {
            System.out.println("Ошибка сервера: " + response.getMessage());
            return;
        }

        if (response.getCollectionInfo() != null) {
            printInfo(response.getCollectionInfo());
            return;
        }

        if (!response.getFlats().isEmpty()) {
            printFlats(response.getFlats());
            return;
        }

        if (response.getFlat() != null) {
            printFlat(response.getMessage(), response.getFlat());
            return;
        }

        if (response.getMessage() != null && !response.getMessage().isEmpty()) {
            System.out.println(response.getMessage());
        }
    }

    private void printInfo(CollectionInfo info) {
        System.out.println("Тип коллекции: " + info.getCollectionType());
        System.out.println("Дата инициализации: " + info.getInitializationDate());
        System.out.println("Количество элементов: " + info.getSize());
        System.out.println("Файл хранения: " + info.getStorageFileName());
    }

    private void printFlats(List<Flat> flats) {
        for (Flat flat : flats) {
            System.out.println(flat);
        }
    }

    private void printFlat(String header, Flat flat) {
        if (header != null && !header.isEmpty() && !"Коллекция пуста.".equals(header)) {
            System.out.println(header);
        }
        System.out.println(flat);
    }
}
