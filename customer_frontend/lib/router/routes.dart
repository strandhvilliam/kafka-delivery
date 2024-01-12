import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';

import '../screens/find_screen.dart';
import '../screens/home_screen.dart';
import '../screens/login_screen.dart';
import '../screens/restaurant_screen.dart';

part 'routes.g.dart';

final GlobalKey<NavigatorState> rootNavigatorKey =
    GlobalKey<NavigatorState>(debugLabel: 'rootKey');
final GlobalKey<NavigatorState> shellNavigatorKey =
    GlobalKey<NavigatorState>(debugLabel: 'shellKey');

@TypedShellRoute<HomeRoute>(routes: <TypedGoRoute<GoRouteData>>[
  TypedGoRoute<FindRoute>(
    path: '/find',
    routes: <TypedGoRoute<GoRouteData>>[
      TypedGoRoute<RestaurantRoute>(path: 'restaurant/:id'),
    ],
  ),
  TypedGoRoute<OrdersRoute>(path: '/orders'),
  TypedGoRoute<ProfileRoute>(path: '/profile')
])
class HomeRoute extends ShellRouteData {
  const HomeRoute();

  static final GlobalKey<NavigatorState> $navigatorKey = shellNavigatorKey;

  @override
  Widget builder(BuildContext context, GoRouterState state, Widget navigator) {
    return HomeScreen(child: Center(child: navigator));
  }
}

class RestaurantRoute extends GoRouteData {
  RestaurantRoute({required this.id});
  final String id;
  @override
  NoTransitionPage<void> buildPage(BuildContext context, GoRouterState state) =>
      NoTransitionPage<void>(
        key: state.pageKey,
        child: RestaurantScreen(id: id),
      );
}

class ProfileRoute extends GoRouteData {
  @override
  NoTransitionPage<void> buildPage(BuildContext context, GoRouterState state) =>
      NoTransitionPage<void>(
        key: state.pageKey,
        child: const Scaffold(
          body: Center(
            child: Text('Profile'),
          ),
        ),
      );
}

class FindRoute extends GoRouteData {
  @override
  NoTransitionPage<void> buildPage(BuildContext context, GoRouterState state) =>
      NoTransitionPage<void>(
        key: state.pageKey,
        child: FindScreen(),
      );
}

class OrdersRoute extends GoRouteData {
  @override
  NoTransitionPage<void> buildPage(BuildContext context, GoRouterState state) =>
      NoTransitionPage<void>(
        key: state.pageKey,
        child: const Scaffold(
          body: Center(
            child: Text('Orders'),
          ),
        ),
      );
}

@TypedGoRoute<LoginRoute>(path: '/login')
class LoginRoute extends GoRouteData {
  const LoginRoute();

  @override
  Widget build(BuildContext context, GoRouterState state) {
    return const LoginScreen();
  }
}

@TypedGoRoute<SplashRoute>(path: '/splash')
class SplashRoute extends GoRouteData {
  const SplashRoute();

  @override
  Widget build(BuildContext context, GoRouterState state) {
    return const Scaffold(
      body: Center(
        child: Text('Splash'),
      ),
    );
  }
}
