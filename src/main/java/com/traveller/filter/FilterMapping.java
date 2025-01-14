package com.traveller.filter;

import com.traveller.mapping.AbstractMapping;
import jakarta.servlet.Filter;

public class FilterMapping extends AbstractMapping {
    public  final Filter filter;



    public FilterMapping(String pattern, Filter filter) {
        super(pattern);
        this.filter = filter;
    }

}
