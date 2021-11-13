import io.sentry.Sentry;

Sentry.init(options -> {
  options.setDsn("https://16be66f2ddf34e8184bff9d57b74bc68@o593844.ingest.sentry.io/6062295");
  // Set tracesSampleRate to 1.0 to capture 100% of transactions for performance monitoring.
  // We recommend adjusting this value in production.
  options.setTracesSampleRate(1.0);
  // When first trying Sentry it's good to see what the SDK is doing:
  options.setDebug(true);
});
