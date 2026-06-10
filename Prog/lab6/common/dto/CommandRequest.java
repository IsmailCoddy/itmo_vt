package common.dto;

import java.io.Serializable;


public class CommandRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private final CommandType type;
    private final Long id;
    private final Boolean centralHeating;
    private final String textArgument;
    private final FlatData flatData;

    private CommandRequest(CommandType type, Long id, Boolean centralHeating, String textArgument, FlatData flatData) {
        this.type = type;
        this.id = id;
        this.centralHeating = centralHeating;
        this.textArgument = textArgument;
        this.flatData = flatData;
    }

    public static CommandRequest simple(CommandType type) {
        return new CommandRequest(type, null, null, null, null);
    }

    public static CommandRequest withId(CommandType type, long id) {
        return new CommandRequest(type, id, null, null, null);
    }

    public static CommandRequest withBoolean(CommandType type, boolean value) {
        return new CommandRequest(type, null, value, null, null);
    }

    public static CommandRequest withText(CommandType type, String value) {
        return new CommandRequest(type, null, null, value, null);
    }

    public static CommandRequest add(FlatData flatData) {
        return new CommandRequest(CommandType.ADD, null, null, null, flatData);
    }

    public static CommandRequest update(long id, FlatData flatData) {
        return new CommandRequest(CommandType.UPDATE, id, null, null, flatData);
    }

    public CommandType getType() {
        return type;
    }

    public Long getId() {
        return id;
    }

    public Boolean getCentralHeating() {
        return centralHeating;
    }

    public String getTextArgument() {
        return textArgument;
    }

    public FlatData getFlatData() {
        return flatData;
    }
}
