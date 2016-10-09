package com.sda.iManu.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

/**
 * Created by teos on 2016-10-03.
 */
@Getter
@Setter
@ToString
public class Message {
    @Id
    private String id;

    private String date;

    private String from;

    private String to;

    private String travelId;

    private String topic;

    private String message;

    public Message(String id, String date, String from, String to, String travelId, String topic, String message) {
        this.id = id;
        this.date = date;
        this.from = from;
        this.to = to;
        this.travelId = travelId;
        this.topic = topic;
        this.message = message;
    }

}
