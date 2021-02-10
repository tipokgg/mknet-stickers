package ru.mknet.stickers.util;

import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class StickersData {

    private HashMap<String, Sticker> stickerData = new HashMap<>();

    public HashMap<String, Sticker> getStickerData() {
        return stickerData;
    }

    public void setStickerData(HashMap<String, Sticker> stickerData) {
        this.stickerData = stickerData;
    }

    public void putSticker(String key, Sticker sticker) {
        this.stickerData.put(key, sticker);
    }

    public void clear() {
        this.stickerData.clear();
    }
}
