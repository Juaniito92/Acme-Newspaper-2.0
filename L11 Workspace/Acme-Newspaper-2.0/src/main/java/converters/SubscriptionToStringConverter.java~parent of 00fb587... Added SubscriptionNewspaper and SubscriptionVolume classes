
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.SubscriptionNewspaper;

@Component
@Transactional
public class SubscriptionToStringConverter implements Converter<SubscriptionNewspaper, String> {

	@Override
	public String convert(final SubscriptionNewspaper subscription) {
		String result;

		if (subscription == null)
			result = null;
		else
			result = String.valueOf(subscription.getId());

		return result;
	}
}
