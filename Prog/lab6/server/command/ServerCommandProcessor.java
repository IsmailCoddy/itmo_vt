package server.command;

import collection.FlatCollection;
import common.CommandDescriptions;
import common.dto.CollectionInfo;
import common.dto.CommandRequest;
import common.dto.CommandResponse;
import common.dto.FlatData;
import model.Flat;
import storage.FlatStorage;
import storage.StorageException;

import java.time.ZonedDateTime;
import java.util.stream.Collectors;


public class ServerCommandProcessor {
    private final FlatCollection collection;
    private final FlatStorage storage;


    public ServerCommandProcessor(FlatCollection collection, FlatStorage storage) {
        this.collection = collection;
        this.storage = storage;
    }


    public CommandResponse execute(CommandRequest request) {
        if (request == null || request.getType() == null) {
            return CommandResponse.message(false, "Пустой или некорректный запрос");
        }

        try {
            switch (request.getType()) {
                case HELP:
                    return help();
                case INFO:
                    return info();
                case SHOW:
                    return show();
                case ADD:
                    return add(request);
                case UPDATE:
                    return update(request);
                case REMOVE_BY_ID:
                    return removeById(request);
                case CLEAR:
                    return clear();
                case HEAD:
                    return CommandResponse.flat("Коллекция пуста.", collection.head());
                case REMOVE_HEAD:
                    return removeHead();
                case MIN_BY_CENTRAL_HEATING:
                    return CommandResponse.flat("Коллекция пуста.", collection.minByCentralHeating());
                case COUNT_LESS_THAN_CENTRAL_HEATING:
                    return countLessThanCentralHeating(request);
                case FILTER_CONTAINS_NAME:
                    return filterContainsName(request);
                default:
                    return CommandResponse.message(false, "Неизвестный тип команды: " + request.getType());
            }
        } catch (IllegalArgumentException | IllegalStateException exception) {
            return CommandResponse.message(false, exception.getMessage());
        }
    }


    public String saveCollection() {
        try {
            storage.save(collection.sortedElements());
            return "Коллекция сохранена в файл: " + storage.getFileName();
        } catch (StorageException exception) {
            String message = "Ошибка сохранения: " + exception.getMessage();
            if (exception.getCause() != null) {
                message += ". Причина: " + exception.getCause().getMessage();
            }
            return message;
        }
    }

    private CommandResponse help() {
        String message = CommandDescriptions.clientCommands().stream()
                .collect(Collectors.joining(System.lineSeparator()));
        return CommandResponse.message(true, message);
    }

    private CommandResponse info() {
        CollectionInfo info = new CollectionInfo(
                collection.getCollectionType(),
                collection.getInitializationDate(),
                collection.size(),
                storage.getFileName()
        );
        return CommandResponse.info(info);
    }

    private CommandResponse show() {
        return CommandResponse.flats("Коллекция пуста.", collection.sortedByName());
    }

    private CommandResponse add(CommandRequest request) {
        FlatData data = requireFlatData(request);
        long id = collection.generateId();
        Flat flat = data.toFlat(id, ZonedDateTime.now());
        collection.add(flat);
        return CommandResponse.message(true, "Элемент добавлен. id = " + id);
    }

    private CommandResponse update(CommandRequest request) {
        long id = requireId(request);
        Flat old = collection.getById(id);
        if (old == null) {
            return CommandResponse.message(true, "Элемент с id " + id + " не найден.");
        }

        Flat updated = requireFlatData(request).toFlat(id, old.getCreationDate());
        collection.update(id, updated);
        return CommandResponse.message(true, "Элемент с id " + id + " обновлен.");
    }

    private CommandResponse removeById(CommandRequest request) {
        long id = requireId(request);
        Flat removed = collection.removeById(id);
        if (removed == null) {
            return CommandResponse.message(true, "Элемент с id " + id + " не найден.");
        }
        return CommandResponse.flat("Элемент удален:", removed);
    }

    private CommandResponse clear() {
        collection.clear();
        return CommandResponse.message(true, "Коллекция очищена.");
    }

    private CommandResponse removeHead() {
        Flat removed = collection.removeHead();
        if (removed == null) {
            return CommandResponse.message(true, "Коллекция пуста.");
        }
        return CommandResponse.flat("Первый элемент удален:", removed);
    }

    private CommandResponse countLessThanCentralHeating(CommandRequest request) {
        boolean value = requireCentralHeating(request);
        long count = collection.countLessThanCentralHeating(value);
        return CommandResponse.message(true, String.valueOf(count));
    }

    private CommandResponse filterContainsName(CommandRequest request) {
        String name = requireTextArgument(request, "name");
        return CommandResponse.flats("Элементы не найдены.", collection.filterContainsName(name));
    }

    private long requireId(CommandRequest request) {
        if (request.getId() == null) {
            throw new IllegalArgumentException("Не указан id");
        }
        return request.getId();
    }

    private boolean requireCentralHeating(CommandRequest request) {
        if (request.getCentralHeating() == null) {
            throw new IllegalArgumentException("Не указан centralHeating");
        }
        return request.getCentralHeating();
    }

    private String requireTextArgument(CommandRequest request, String argumentName) {
        String value = request.getTextArgument();
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Не указан аргумент " + argumentName);
        }
        return value.trim();
    }

    private FlatData requireFlatData(CommandRequest request) {
        if (request.getFlatData() == null) {
            throw new IllegalArgumentException("Не передан объект FlatData");
        }
        return request.getFlatData();
    }
}
