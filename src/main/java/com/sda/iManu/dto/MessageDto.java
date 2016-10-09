package com.sda.iManu.dto;

import com.sda.iManu.domain.Message;
import com.sda.iManu.domain.Rating;
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
public class MessageDto {
    @Id
    private String id;

    private String date;

    private String from;

    private String fromName;

    private String to;

    private String travelId;

    private String travelName;

    private String topic;

    private String message;

    public static MessageDto fromMessage(final Message message) {
        final MessageDto result = new MessageDto();
        result.setId(message.getId());
        result.setDate(message.getDate());
        result.setFrom(message.getFrom());
        result.setTo(message.getTo());
        result.setTravelId(message.getTravelId());
        result.setTopic(message.getTopic());
        result.setMessage(message.getMessage());
        return result;
    }
}
