package com.sda.iManu.web.controller;

import com.sda.iManu.converter.RegistrationDtoToUserConverter;
import com.sda.iManu.domain.Access;
import com.sda.iManu.domain.Country;
import com.sda.iManu.domain.Travel;
import com.sda.iManu.dto.SearchDto;
import com.sda.iManu.dto.TravelDto;
import com.sda.iManu.service.impl.TravelService;
import com.sda.iManu.service.impl.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by teos on 2016-09-28.
 */
@Controller
@RequestMapping("/")
public class SearchController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterController.class);


    private RegistrationDtoToUserConverter converter
            = new RegistrationDtoToUserConverter();

    @Autowired
    private UserService userService;

    @Autowired
    TravelService travelService;

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView search(@ModelAttribute(value = "data") @Valid SearchDto searchDto,
                               BindingResult result,
                               ModelMap model) {
        LOGGER.info("Search POST {} ", searchDto);

        ModelAndView modelAndView = prepareModelAndView(searchDto);
        modelAndView.setViewName("search");
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView startSearch(@ModelAttribute SearchDto searchDto, ModelMap model) {
        LOGGER.info("search get");

        ModelAndView modelAndView = prepareModelAndView(searchDto);
        modelAndView.setViewName("search");
        return modelAndView;
    }

    private ModelAndView prepareModelAndView(SearchDto searchDto) {
        ModelAndView modelAndView = new ModelAndView();
        LinkedList<Country> countries = new LinkedList<>();
        for (Country current : Country.values()) {
            LOGGER.info("pojedynczy kraj {} ", current);
            countries.add(current);
        }
        LinkedList<Access> accesses = new LinkedList<>();
        for (Access current : Access.values()) {
            accesses.add(current);
        }
        LOGGER.info("prepare search obj {}", searchDto);
        List<TravelDto> travels = prepareSearchResults(searchDto);

        modelAndView.addObject("searchDto", searchDto);
        modelAndView.addObject("countries", countries);
        modelAndView.addObject("accesses", accesses);
        modelAndView.addObject("travelList", travels);
        return modelAndView;
    }

    private List<TravelDto> prepareSearchResults(SearchDto searchDto) {
        List<TravelDto> travelsDto;
        List<Travel> travels;
        String selectedCountry = searchDto.getSelectedCountry();
        String selectedAccess = searchDto.getSelectedAccess();
        String dateFrom = searchDto.getDateFrom();
        String dateTo = searchDto.getDateTo();
        String expression = searchDto.getExpression();

        boolean countrySearchOn = selectedCountry != null && !"WYBIERZ".equals(selectedCountry);
        boolean countrySearchOff = (selectedCountry == null || "WYBIERZ".equals(selectedCountry));

        boolean accessSearchOn = selectedAccess != null && !"WYBIERZ".equals(selectedAccess);
        boolean accessSearchOff = (selectedAccess == null || "WYBIERZ".equals(selectedAccess));

        boolean dateSearchOn = dateFrom != null && !"".equals(dateFrom) && dateTo != null && !"".equals(dateTo);
        boolean dateSearchOff = dateFrom == null || "".equals(dateFrom) || dateTo == null || "".equals(dateTo);

        boolean expressionOn = expression != null && !"".equals(expression);
        boolean expressionOff = expression == null || "".equals(expression);

        if (countrySearchOn && accessSearchOff && dateSearchOff && expressionOff) {
            LOGGER.info("Selected country not null: {}", selectedCountry);
            travels = travelService.getTravelsByCountry(selectedCountry);

        } else if (countrySearchOff && accessSearchOff && dateSearchOff && expressionOn) {
            LOGGER.info("Selected exp not null: {}", expression);
            travels = travelService
                    .getTravelsByExp(expression);

        } else if (countrySearchOn && accessSearchOff && dateSearchOff && expressionOn) {
            LOGGER.info("Selected country and exp not null: {}", selectedCountry, expression);
            travels = travelService
                    .getTravelsByCountryAndExp(selectedCountry, expression);

        } else if (countrySearchOn && accessSearchOn && dateSearchOff && expressionOff) {
            LOGGER.info("Selected country and access not null: {}", selectedCountry, selectedAccess);
            travels = travelService
                    .getTravelsByCountryAndAccess(selectedCountry, selectedAccess);

        } else if (countrySearchOn && accessSearchOn && dateSearchOff && expressionOn) {
            LOGGER.info("Selected country, access and exp not null: {}", selectedCountry, selectedAccess, expression);
            travels = travelService
                    .getTravelsByCountryAndAccessAndExp(selectedCountry, selectedAccess, expression);

        } else if (countrySearchOn && accessSearchOff && dateSearchOn && expressionOff) {
            LOGGER.info("Selected country and date range not null: {}", selectedCountry, dateFrom, dateTo);
            travels = travelService
                    .getTravelsByCountryAndDate(selectedCountry, dateFrom, dateTo);

        } else if (countrySearchOn && accessSearchOff && dateSearchOn && expressionOn) {
            LOGGER.info("Selected country, date range and exp not null: {}", selectedCountry, dateFrom, dateTo, expression);
            travels = travelService
                    .getTravelsByCountryAndDateAndExp(selectedCountry, dateFrom, dateTo, expression);

        } else if (countrySearchOn && accessSearchOn && dateSearchOn && expressionOff) {
            LOGGER.info("Selected country, access and date not null: {}", selectedCountry, selectedAccess, dateFrom, dateTo);
            travels = travelService
                    .getTravelsByCountryAndAccessAndDate(selectedCountry, selectedAccess, dateFrom, dateTo);

        } else if (countrySearchOn && accessSearchOn && dateSearchOn && expressionOn) {
            LOGGER.info("Selected country, access, date and exp not null: {}", selectedCountry, selectedAccess, dateFrom, dateTo, expression);
            travels = travelService
                    .getTravelsByCountryAndAccessAndDateAndExp(selectedCountry, selectedAccess, dateFrom, dateTo, expression);

        } else if (countrySearchOff && accessSearchOn && dateSearchOff && expressionOff) {
            LOGGER.info("Selected access not null: {}", selectedAccess);
            travels = travelService
                    .getTravelsByAccess(selectedAccess);

        } else if (countrySearchOff && accessSearchOn && dateSearchOff && expressionOn) {
            LOGGER.info("Selected access and exp not null: {}", selectedAccess, expression);
            travels = travelService
                    .getTravelsByAccessAndExp(selectedAccess, expression);

        } else if (countrySearchOff && accessSearchOn && dateSearchOn && expressionOff) {
            LOGGER.info("Selected access and date range not null: {}", selectedAccess);
            travels = travelService
                    .getTravelsByAccessAndDate(selectedAccess, dateFrom, dateTo);

        } else if (countrySearchOff && accessSearchOn && dateSearchOn && expressionOn) {
            LOGGER.info("Selected access, date range and exp not null: {}", selectedAccess, expression);
            travels = travelService
                    .getTravelsByAccessAndDateAndExp(selectedAccess, dateFrom, dateTo, expression);

        } else if (countrySearchOff && accessSearchOff && dateSearchOn && expressionOff) {
            LOGGER.info("Selected date range not null: {}", dateFrom, dateTo);
            travels = travelService
                    .getTravelsByDate(dateFrom, dateTo);

        }else if (countrySearchOff && accessSearchOff && dateSearchOn && expressionOn) {
            LOGGER.info("Selected date range and exp not null: {}", dateFrom, dateTo, expression);
            travels = travelService
                    .getTravelsByDateAndExp(dateFrom, dateTo, expression);

        } else {
            travels = travelService.getTravels();
            LOGGER.info("Travels list: {}", travels);
        }

        ////szalony plan
        travelsDto = travels.stream()
                .map(TravelDto::fromTravel)
                .collect(Collectors.toCollection(LinkedList::new));


        return travelsDto;
    }


//    private List<TravelDto> searchByCountry(String selectedCountry) {
//        List<TravelDto> travels = travelService
//                .getTravelsByCountry(selectedCountry)
//                .stream()
//                .map(TravelDto::fromTravel)
//                .collect(Collectors.toCollection(LinkedList::new));
//        LOGGER.info("Travels by country list: {}", travels);
//        return travels;
//    }

//    private List<TravelDto> searchByExp(String expression) {
//        List<TravelDto> travels = travelService
//                .getTravelsByExp(expression)
//                .stream()
//                .map(TravelDto::fromTravel)
//                .collect(Collectors.toCollection(LinkedList::new));
//        LOGGER.info("Travels by exp list: {}", travels);
//        return travels;
//    }

//    private List<TravelDto> searchByCountryAndExp(String selectedCountry, String expression) {
//        List<TravelDto> travels = travelService
//                .getTravelsByCountryAndExp(selectedCountry, expression)
//                .stream()
//                .map(TravelDto::fromTravel)
//                .collect(Collectors.toCollection(LinkedList::new));
//        LOGGER.info("Travels by country and exp list: {}", travels);
//        return travels;
//    }
//
//    private List<TravelDto> searchByCountryAndAccess(String selectedCountry, String selectedAccess) {
//        List<TravelDto> travels = travelService
//                .getTravelsByCountryAndAccess(selectedCountry, selectedAccess)
//                .stream()
//                .map(TravelDto::fromTravel)
//                .collect(Collectors.toCollection(LinkedList::new));
//        LOGGER.info("Travels by country and access list: {}", travels);
//        return travels;
//    }
//
//    private List<TravelDto> searchByCountryAndAccessAndExp(String selectedCountry, String selectedAccess, String expression) {
//        List<TravelDto> travels = travelService
//                .getTravelsByCountryAndAccessAndExp(selectedCountry, selectedAccess, expression)
//                .stream()
//                .map(TravelDto::fromTravel)
//                .collect(Collectors.toCollection(LinkedList::new));
//        LOGGER.info("Travels by country, access and exp list: {}", travels);
//        return travels;
//    }

//    private List<TravelDto> searchByCountryAndDate(String selectedCountry, String dateFrom, String dateTo) {
//        List<TravelDto> travels = travelService
//                .getTravelsByCountryAndDate(selectedCountry, dateFrom, dateTo)
//                .stream()
//                .map(TravelDto::fromTravel)
//                .collect(Collectors.toCollection(LinkedList::new));
//        LOGGER.info("Travels by country and date list: {}", travels);
//        return travels;
//    }
//    private List<TravelDto> searchByCountryAndDateAndExp(String selectedCountry, String dateFrom, String dateTo, String expression) {
//        List<TravelDto> travels = travelService
//                .getTravelsByCountryAndDateAndExp(selectedCountry, dateFrom, dateTo, expression)
//                .stream()
//                .map(TravelDto::fromTravel)
//                .collect(Collectors.toCollection(LinkedList::new));
//        LOGGER.info("Travels by country, date and exp list: {}", travels);
//        return travels;
//    }
//    private List<TravelDto> searchByCountryAndAccessAndDate(String selectedCountry, String selectedAccess, String dateFrom, String dateTo) {
//        List<TravelDto> travels = travelService
//                .getTravelsByCountryAndAccessAndDate(selectedCountry, selectedAccess, dateFrom, dateTo)
//                .stream()
//                .map(TravelDto::fromTravel)
//                .collect(Collectors.toCollection(LinkedList::new));
//        LOGGER.info("Travels by country, access and date list: {}", travels);
//        return travels;
//    }

//    private List<TravelDto> searchByCountryAndAccessAndDateAndExp(String selectedCountry, String selectedAccess, String dateFrom, String dateTo, String expression) {
//        List<TravelDto> travels = travelService
//                .getTravelsByCountryAndAccessAndDateAndExp(selectedCountry, selectedAccess, dateFrom, dateTo, expression)
//                .stream()
//                .map(TravelDto::fromTravel)
//                .collect(Collectors.toCollection(LinkedList::new));
//        LOGGER.info("Travels by country, access, date and exp list: {}", travels);
//        return travels;
//    }
//
//    private List<TravelDto> searchByAccess(String selectedAccess) {
//        List<TravelDto> travels = travelService
//                .getTravelsByAccess(selectedAccess)
//                .stream()
//                .map(TravelDto::fromTravel)
//                .collect(Collectors.toCollection(LinkedList::new));
//        LOGGER.info("Travels by access list: {}", travels);
//        return travels;
//    }

//    private List<TravelDto> searchByAccessAndExp(String selectedAccess, String expression) {
//        List<TravelDto> travels = travelService
//                .getTravelsByAccessAndExp(selectedAccess, expression)
//                .stream()
//                .map(TravelDto::fromTravel)
//                .collect(Collectors.toCollection(LinkedList::new));
//        LOGGER.info("Travels by access and exp list: {}", travels);
//        return travels;
//    }

//    private List<TravelDto> searchByAccessAndDate(String selectedAccess, String dateFrom, String dateTo) {
//        List<TravelDto> travels = travelService
//                .getTravelsByAccessAndDate(selectedAccess, dateFrom, dateTo)
//                .stream()
//                .map(TravelDto::fromTravel)
//                .collect(Collectors.toCollection(LinkedList::new));
//
//        LOGGER.info("Travels by access and date list: {}", travels);
//        return travels;
//    }
//
//    private List<TravelDto> searchByAccessAndDateAndExp(String selectedAccess, String dateFrom, String dateTo, String expression) {
//        List<TravelDto> travels = travelService
//                .getTravelsByAccessAndDateAndExp(selectedAccess, dateFrom, dateTo, expression)
//                .stream()
//                .map(TravelDto::fromTravel)
//                .collect(Collectors.toCollection(LinkedList::new));
//        LOGGER.info("Travels by access, date and exp list: {}", travels);
//        return travels;
//    }

//    private List<TravelDto> searchByDate(String dateFrom, String dateTo) {
//        List<TravelDto> travels = travelService
//                .getTravelsByDate(dateFrom, dateTo)
//                .stream()
//                .map(TravelDto::fromTravel)
//                .collect(Collectors.toCollection(LinkedList::new));
//        LOGGER.info("Travels by date list: {}", travels);
//        return travels;
//    }
//
//    private List<TravelDto> searchByDateAndExp(String dateFrom, String dateTo, String expression) {
//        List<TravelDto> travels = travelService
//                .getTravelsByDateAndExp(dateFrom, dateTo, expression)
//                .stream()
//                .map(TravelDto::fromTravel)
//                .collect(Collectors.toCollection(LinkedList::new));
//        LOGGER.info("Travels by date and exp list: {}", travels);
//        return travels;
//    }


}
