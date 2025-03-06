package com.example.Library.entities;

public enum StatusReservation {
    PENDING {
        @Override
        public boolean isNextStatePossible(StatusReservation nextStatus) {
            return nextStatus == IN_PROGRESS;
        }
    },
    IN_PROGRESS {
        @Override
        public boolean isNextStatePossible(StatusReservation nextStatus) {
            return nextStatus == FINISHED;
        }
    },
    DELAYED {
        @Override
        public boolean isNextStatePossible(StatusReservation nextStatus) {
            return nextStatus == FINISHED;
        }
    },
    FINISHED {
        @Override
        public boolean isNextStatePossible(StatusReservation nextStatus) {
            return false;
        }
    },
    CANCELED {
        @Override
        public boolean isNextStatePossible(StatusReservation nextStatus) {
            return false;
        }
    };

    public abstract boolean isNextStatePossible(StatusReservation statusReservation);
}
