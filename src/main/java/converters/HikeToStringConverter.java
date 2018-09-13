package converters;

import domain.Hike;
import domain.Route;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class HikeToStringConverter implements Converter<Hike, String>{
	
	@Override
	public String convert(final Hike folder) {
		String result;

		if (folder == null)
			result = null;
		else
			result = String.valueOf(folder.getId());

		return result;
	}

}
