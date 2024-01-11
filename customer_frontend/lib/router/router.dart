import 'package:customer_frontend/router/routes.dart';
import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';
import 'package:riverpod_annotation/riverpod_annotation.dart';

import '../state/auth_controller.dart';

part 'router.g.dart';

@riverpod
GoRouter router(RouterRef ref) {
  final isAuth = ValueNotifier<AsyncValue<bool>>(const AsyncLoading());

  ref
    ..onDispose(isAuth.dispose)
    ..listen(
      authControllerProvider
          .select((value) => value.whenData((value) => value.isAuth)),
      (_, next) => isAuth.value = next,
    );

  final router = GoRouter(
      navigatorKey: rootNavigatorKey,
      refreshListenable: isAuth,
      routes: $appRoutes,
      debugLogDiagnostics: true,
      initialLocation: const SplashRoute().location,
      redirect: (context, state) {
        final isAuthError = isAuth.value.unwrapPrevious().hasError;
        if (isAuthError) {
          return const LoginRoute().location;
        }
        final isAuthLoading = isAuth.value.isLoading || !isAuth.value.hasValue;
        if (isAuthLoading) {
          return const SplashRoute().location;
        }

        final auth = isAuth.value.requireValue;

        final isSplash = state.uri.path == const SplashRoute().location;
        if (isSplash) {
          return auth ? FindRoute().location : const LoginRoute().location;
        }
        final isLoggingIn = state.uri.path == const LoginRoute().location;
        if (isLoggingIn) {
          return auth ? FindRoute().location : null;
        }

        return auth ? null : const SplashRoute().location;
      });

  ref.onDispose(router.dispose);
  return router;
}
