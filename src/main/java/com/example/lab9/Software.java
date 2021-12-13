package com.example.lab9;

public class Software {
    public static int Id = 0;

    private String name;
    private String description;
    private float cost;
    private String date;
    private String version;
    private String categories;
    private String subcategories;

    private int id;

    public Software(Integer id, String name, String description, float cost, String date, String version, String categories, String subcategories) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.date = date;
        this.version = version;
        this.categories = categories;
        this.subcategories = subcategories;
        if (id != -1){
            Id += 1;
        }
    }

    public String getInfo(){
        String info = "Имя программного обеспечения: " + this.name + " Версия " + this.version + "\n";
        info += "Дата создания: " + this.date + "\n";
        info += "Категория: " + this.categories + "\n";
        info += "Подкатегория: " + this.subcategories + "\n";
        info += "Стоимость: " + this.cost + "\n";
        info += "Опиание: " + this.description + "\n";
        return info;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public String getCategories() {
        return categories;
    }

    public String getSubcategories() {
        return subcategories;
    }

    public String getDescription() {
        return description;
    }

    public float getCost() {
        return cost;
    }

    public String getDate() {
        return date;
    }

}
