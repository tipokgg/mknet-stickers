package ru.mknet.stickers.util;

import ru.mknet.stickers.qrcode.QRGenerator;

public class Sticker {

    private String ssid;
    private String password;
    private String qrcode;


    public Sticker(String ssid, String password) {
        this.ssid = ssid;
        this.password = password;
        this.qrcode = QRGenerator.generateBase64Qr(ssid, password);
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHtmlString() {
        return "<b>Имя сети:</b><br>" + ssid + "<br>" + "<b>Пароль:</b><br>" + password;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }
}
