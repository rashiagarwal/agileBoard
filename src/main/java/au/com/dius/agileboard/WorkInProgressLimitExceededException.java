package au.com.dius.agileboard;

class WorkInProgressLimitExceededException extends RuntimeException {
  WorkInProgressLimitExceededException(String message) {
    super(message);
  }
}
