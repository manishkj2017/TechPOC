package shop.client.jms;

import java.math.RoundingMode;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.MessageTransformer;

import shop.core.domain.PetOrder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONTransformer implements MessageTransformer{

	@Override
	public Message consumerTransform(Session arg0, MessageConsumer arg1,
			Message arg2) throws JMSException {
		
		return null; //not required at the moment
	}

	@Override
	public Message producerTransform(Session session, MessageProducer producer,
			Message message) throws JMSException {
		ObjectMapper mapper = new ObjectMapper();
		PetOrder order = (PetOrder)((ObjectMessage)message).getObject();
		order.setBidPrice(order.getBidPrice().setScale(2, RoundingMode.HALF_EVEN));
		
		try{
			String jsonMessage = mapper.writeValueAsString(order);
			return session.createTextMessage(jsonMessage);
		}catch(JsonProcessingException e){
			e.printStackTrace();
		}
		return null;
	}
	
}
