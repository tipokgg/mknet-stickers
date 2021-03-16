package ru.mknet.stickers.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.mknet.stickers.dao.SQLConnector;
import ru.mknet.stickers.util.GenerateByMac;
import ru.mknet.stickers.util.Sticker;
import ru.mknet.stickers.util.StickersData;

import java.util.HashMap;
import java.util.List;

@Controller
public class MainController {

    private final StickersData stickersData;
    private final SQLConnector sqlConnector;
    private final GenerateByMac generateByMac;

    public MainController(StickersData stickersData, SQLConnector sqlConnector, GenerateByMac generateByMac) {
        this.stickersData = stickersData;
        this.sqlConnector = sqlConnector;
        this.generateByMac = generateByMac;
    }


    @GetMapping
    public String index(Model model) {
        return "modalForm";
    }

    @GetMapping(path = "pagea4")
    public String pagea4(Model model) {

        stickersData.getStickerData().forEach((key, value) -> {
            model.addAttribute("cell" + key, value.getHtmlString());
            model.addAttribute("qr" + key, value.getQrcode());
        });

        return "pagea4";
    }


    @PostMapping("/addNew")
    public String addNew(@RequestParam String column, String row, String contract,
                         String macaddr, String customssid, String custompass, Boolean clearBox, Model model) {

        if (clearBox != null) stickersData.clear();

        if (customssid.length() > 0 && custompass.length() > 8) {
            Sticker sticker = new Sticker(customssid.trim(), custompass.trim());
            stickersData.putSticker(row + column, sticker);
        } else if (!contract.isEmpty()) {
            HashMap<String, String> map = sqlConnector.getLoginAndPassByContract(contract.trim());
            String login = "";
            String pass = "";
            for (String l : map.keySet()) {
                login = l;
                pass = map.get(l);
            }

            Sticker sticker = new Sticker("MK-NET_" + login, login + pass);

            stickersData.putSticker(row + column, sticker);
        } else if (!macaddr.isEmpty()) {
            List<String> list = generateByMac.getData(macaddr.trim());
            Sticker sticker = null;
            if (list != null) {
               sticker = new Sticker(list.get(0), list.get(1));
            }
            stickersData.putSticker(row + column, sticker);
        }

        return "redirect:/pagea4";
    }
}
