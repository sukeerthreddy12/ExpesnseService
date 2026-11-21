package com.expesne.service.consumer;


import com.expesne.service.dto.ExpenseDto;
import org.apache.kafka.common.serialization.Deserializer;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.util.Map;

public class ExpenseDeserializer implements Deserializer<ExpenseDto> {

    @Override
    public void close() {
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public ExpenseDto deserialize(String arg0, byte[] arg1) {
        ObjectMapper mapper = new ObjectMapper();
        ExpenseDto expense = null;
        try {
            JsonNode node = mapper.readTree(arg1);
            if (node != null && node.isString()) {
                expense = mapper.readValue(node.asString(), ExpenseDto.class);
            } else {
                expense = mapper.treeToValue(node, ExpenseDto.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return expense;
    }
}
