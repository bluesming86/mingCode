package com.ming.learning.design.strategy.game;

public enum GameDetailEnum {
    BRNN(1,"百人牛牛", BrnnController.class.getName(),"brnnDetail"),
    DICE(2,"大话骰", DiceController.class.getName(),"diceDetail"),
    HHDZ(3,"大话骰", HhdzController.class.getName(),"hhdzDetail"),
    ;

    private int id;
    private String name;
    private String clazz;
    private String method;

    GameDetailEnum(int id, String name, String clazz, String method) {
        this.id = id;
        this.name = name;
        this.clazz = clazz;
        this.method = method;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public static GameDetailEnum getGameDetailEnumByGameId(Integer gameId){
        if (gameId != null){
            for (GameDetailEnum gameDetailEnum : GameDetailEnum.values()) {
                if (gameDetailEnum.getId() == gameId){
                    return gameDetailEnum;
                }
            }
        }
        return null;
    }
}
