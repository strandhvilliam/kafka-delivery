import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';
import 'package:hooks_riverpod/hooks_riverpod.dart';

import '../router/routes.dart';

class HomeScreen extends ConsumerWidget {
  const HomeScreen({required this.child, super.key});
  final Widget child;

  int getSelectedIndex(BuildContext context) {
    final String location = GoRouterState.of(context).uri.path;

    return switch (location) {
      (String loc) when FindRoute().location == loc => 0,
      (String loc) when OrdersRoute().location == loc => 1,
      (String loc) when ProfileRoute().location == loc => 2,
      _ => 0,
    };
  }

  void onDestinationSelected(BuildContext context, int index) {
    switch (index) {
      case 0:
        FindRoute().go(context);
      case 1:
        OrdersRoute().go(context);
      case 2:
        ProfileRoute().go(context);
    }
  }

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final int selectedIndex = getSelectedIndex(context);
    return Scaffold(
      extendBodyBehindAppBar: true,
      body: child,
      bottomNavigationBar: NavigationBar(
        height: 52.0,
        selectedIndex: selectedIndex,
        labelBehavior: NavigationDestinationLabelBehavior.alwaysHide,
        onDestinationSelected: (int index) =>
            onDestinationSelected(context, index),
        destinations: [
          NavigationDestination(
            icon: selectedIndex == 0
                ? Text(
                    String.fromCharCode(Icons.restaurant_rounded.codePoint),
                    style: TextStyle(
                      inherit: false,
                      fontSize: 28.0,
                      fontWeight: FontWeight.w800,
                      color: Theme.of(context).colorScheme.primary,
                      fontFamily: Icons.restaurant_rounded.fontFamily,
                    ),
                  )
                : Icon(
                    Icons.restaurant_outlined,
                    size: 28.0,
                    color: Theme.of(context).colorScheme.primary,
                  ),
            label: 'Find',
          ),
          NavigationDestination(
            icon: selectedIndex == 1
                ? Icon(
                    Icons.shopping_bag_rounded,
                    size: 28.0,
                    color: Theme.of(context).colorScheme.primary,
                  )
                : Icon(
                    Icons.shopping_bag_outlined,
                    size: 28.0,
                    color: Theme.of(context).colorScheme.primary,
                  ),
            label: 'Orders',
          ),
          NavigationDestination(
            icon: selectedIndex == 2
                ? Icon(
                    Icons.person_rounded,
                    size: 28.0,
                    color: Theme.of(context).colorScheme.primary,
                  )
                : Icon(
                    Icons.person_outline,
                    size: 28.0,
                    color: Theme.of(context).colorScheme.primary,
                  ),
            label: 'Profile',
          ),
        ],
      ),
    );
  }
}
