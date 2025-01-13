package com.mercant.real.estate.municipality.utils;

public enum Constant {
    URL_CURRENT_MUNICIPALITY {
        @Override
        public String text() {
            return "http://www.istat.it/storage/codici-unita-amministrative/Elenco-codici-statistici-e-denominazioni-delle-unita-territoriali.zip";
        }
    },
    URL_OLD_MUNICIPALITY {
        @Override
        public String text() {
            return "https://www.istat.it/wp-content/uploads/2024/09/Elenco-comuni-soppressi.zip";
        }
    },
    MUNICIPALITY_CHANNEL {
        @Override
        public String text() {
            return "municipality.channel";// Channel name for event bus messages
        }
    }, CSV_END_FILE {
        @Override
        public String text() {
            return ".csv";
        }
    }, EXCEL_END_FILE {
        @Override
        public String text() {
            return ".xlsx";
        }
    };

    public abstract String text();
}
