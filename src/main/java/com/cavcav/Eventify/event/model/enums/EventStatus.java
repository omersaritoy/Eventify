package com.cavcav.Eventify.event.model.enums;

import lombok.Getter;

@Getter
public enum EventStatus {
    DRAFT("Taslak"),
    PUBLISHED("Yayınlandı"),
    CANCELLED("İptal Edildi"),
    COMPLETED("Tamamlandı");

    private final String turkishLabel;

    EventStatus(String turkishLabel) {
        this.turkishLabel = turkishLabel;
    }

}
