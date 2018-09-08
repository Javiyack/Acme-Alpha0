package converters;

import domain.Folder;
import domain.Route;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import repositories.FolderRepository;
import repositories.RouteRepository;

@Component
@Transactional
public class StringToRouteConverter implements Converter<String, Route> {
	@Autowired
	RouteRepository routeRepository;


	@Override
	public Route convert(final String text) {
		Route result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {			
				id = Integer.valueOf(text);
				result = this.routeRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}
}
