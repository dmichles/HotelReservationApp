package com.example.hotels.component.customfield;

import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.datepicker.DatePicker;

public class DateRangePicker extends CustomField<LocalDateRange> {

    private DatePicker start;
    private DatePicker end;

    public DateRangePicker(String label) {
        this();
        setLabel(label);
    }

    public DateRangePicker() {
        start = new DatePicker();
        start.setPlaceholder("Start date");

        end = new DatePicker();
        end.setPlaceholder("End date");

        add(start, end);
    }

    @Override
    protected LocalDateRange generateModelValue() {
        return new LocalDateRange(start.getValue(), end.getValue());
    }

    @Override
    protected void setPresentationValue(LocalDateRange dateRange) {
        start.setValue(dateRange.getStartDate());
        end.setValue(dateRange.getEndDate());
    }
}


