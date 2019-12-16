package com.example.strategiespielapp;

public class AccountPlanet {
    int accountId;
    String email;
    String username;
    String password;
    long last_online;
    int planetId;
    String name;
    int size;
    int x;
    int y;
    int metall;
    int platinen;
    int treibstoff;
    int account_Id;
    int verteidigung1;
    int verteidigung2;
    int verteidigung3;
    int gebäude1;
    int gebäude2;
    int gebäude3;
    int abbau1;
    int abbau2;
    int abbau3;
    int jäger;
    int kreuzer;
    int zerstörer;
    int temperatur;

    public AccountPlanet(int accountId, String email, String username, String password, long last_online,
                         int planetId, String name, int size, int x, int y, int metall, int platinen,
                         int treibstoff, int account_Id, int verteidigung1, int verteidigung2,
                         int verteidigung3, int gebäude1, int gebäude2, int gebäude3,
                         int abbau1, int abbau2, int abbau3, int jäger, int kreuzer, int zerstörer, int temperatur) {
        this.accountId = accountId;
        this.email = email;
        this.username = username;
        this.password = password;
        this.last_online = last_online;
        this.planetId = planetId;
        this.name = name;
        this.size = size;
        this.x = x;
        this.y = y;
        this.metall = metall;
        this.platinen = platinen;
        this.treibstoff = treibstoff;
        this.account_Id = account_Id;
        this.verteidigung1 = verteidigung1;
        this.verteidigung2 = verteidigung2;
        this.verteidigung3 = verteidigung3;
        this.gebäude1 = gebäude1;
        this.gebäude2 = gebäude2;
        this.gebäude3 = gebäude3;
        this.abbau1 = abbau1;
        this.abbau2 = abbau2;
        this.abbau3 = abbau3;
        this.jäger = jäger;
        this.kreuzer = kreuzer;
        this.zerstörer = zerstörer;
        this.temperatur = temperatur;
    }
}
