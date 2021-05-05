package com.gabriel.taxifee;

import java.util.List;

public class Result {

    private List<String> destinations_addresses;

    private List<String> origin_addresses;

    private List<Rows> rows;

    private String status;


    public List<String> getDestinations_addresses() {
        return destinations_addresses;
    }

    public void setDestinations_addresses(List<String> destinations_addresses) {
        this.destinations_addresses = destinations_addresses;
    }

    public List<String> getOrigin_addresses() {
        return origin_addresses;
    }

    public void setOrigin_addresses(List<String> origin_addresses) {
        this.origin_addresses = origin_addresses;
    }

    public List<Rows> getRows() {
        return rows;
    }

    public void setRows(List<Rows> rows) {
        this.rows = rows;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public class Distance {
        private String text;

        private int value;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }

    public class Duration {

        private String text;

        private int value;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }

    private class Elements {
        private Distance distance;

        private Duration duration;

        private String status;

        public Distance getDistance() {
            return distance;
        }

        public void setDistance(Distance distance) {
            this.distance = distance;
        }

        public Duration getDuration() {
            return duration;
        }

        public void setDuration(Duration duration) {
            this.duration = duration;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    public class Rows {
        private List<Elements> elements;

        public List<Elements> getElements() {
            return this.elements;
        }

        public void setElements(List<Elements> elements) {
            this.elements = elements;
        }
    }
}
