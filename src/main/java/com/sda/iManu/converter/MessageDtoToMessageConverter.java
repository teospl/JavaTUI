package com.sda.iManu.converter;

import com.sda.iManu.domain.Message;
import com.sda.iManu.dto.MessageDto;

/**
 * Created by teos on 2016-10-03.
 */
public class MessageDtoToMessageConverter
    implements IConverter<MessageDto, Message> {

        public Message convert(MessageDto messageDto) {
            final Message result = new Message(
                    messageDto.getId(),
                    messageDto.getDate(),
                    messageDto.getFrom(),
                    messageDto.getTo(),
                    messageDto.getTravelId(),
                    messageDto.getTopic(),
                    messageDto.getMessage());
            return result;
        }
}
