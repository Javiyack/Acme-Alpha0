package converters;

import domain.Hike;
import domain.Route;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import repositories.HikeRepository;
import repositories.RouteRepository;

@Component
@Transactional
public class StringToHikeConverter implements Converter<String, Hike> {
	@Autowired
	HikeRepository repository;


	@Override
	public Hike convert(final String text) {
		Hike result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {			
				id = Integer.valueOf(text);
				result = this.repository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}
}
