import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:hooks_riverpod/hooks_riverpod.dart';
import 'package:google_fonts/google_fonts.dart';

import 'router/router.dart';
import 'utils/state_logger.dart';

void main() {
  runApp(const ProviderScope(
    observers: [StateLogger()],
    child: MainApp(),
  ));
}

class MainApp extends ConsumerWidget {
  const MainApp({super.key});

  // static const backgroundColor = Color.fromRGBO(238, 240, 235, 1);

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final router = ref.watch(routerProvider);
    return MaterialApp.router(
      routerConfig: router,
      title: 'Flutter Demo',
      theme: ThemeData(
        colorScheme: const ColorScheme.light(
          primary: Colors.black87,
          secondary: Colors.black87,
          onPrimary: Colors.white,
          onSecondary: Colors.white,
        ),
        // primarySwatch: Colors.green,
        // scaffoldBackgroundColor: backgroundColor,
        chipTheme: ChipThemeData(
          secondarySelectedColor: Colors.black87,
          surfaceTintColor: Colors.white,
          secondaryLabelStyle: TextStyle(
            color: Colors.white,
          ),
          shape: RoundedRectangleBorder(
            side: const BorderSide(color: Colors.black87, width: 1.4),
            borderRadius: BorderRadius.circular(8),
          ),
          selectedColor: Colors.red,
          labelStyle: const TextStyle(
            color: Colors.black,
          ),
          showCheckmark: false,
          elevation: 1.0,
        ),
        searchViewTheme: SearchViewThemeData(
          // surfaceTintColor: backgroundColor,
          // backgroundColor: backgroundColor,
          dividerColor: Colors.transparent,
          elevation: 1.0,
        ),
        searchBarTheme: SearchBarThemeData(
          shape: MaterialStatePropertyAll(
            StadiumBorder(
              side: const BorderSide(color: Colors.black87, width: 1.6),
            ),
          ),
          // backgroundColor: MaterialStatePropertyAll(Colors.grey[100]),
          surfaceTintColor: MaterialStatePropertyAll(Colors.white),

          elevation: MaterialStatePropertyAll(0.0),
        ),
        navigationBarTheme: const NavigationBarThemeData(
          // backgroundColor: Colors.transparent,
          // surfaceTintColor: Colors.transparent,
          overlayColor: MaterialStatePropertyAll(Colors.transparent),
          indicatorColor: Colors.transparent,
          elevation: 1.0,
        ),
        appBarTheme: const AppBarTheme(
          elevation: 0.0,
          systemOverlayStyle: SystemUiOverlayStyle.dark,
          backgroundColor: Colors.white,
          surfaceTintColor: Colors.transparent,
          centerTitle: true,
          // iconTheme: IconThemeData(color: Colors.black87),
          // actionsIconTheme: IconThemeData(color: Colors.black87),
        ),
        textTheme: TextTheme(
          titleSmall: TextStyle(
            letterSpacing: 1.0,
            fontSize: 24.0,
            fontFamily: GoogleFonts.abrilFatface().fontFamily,
          ),
          titleLarge: TextStyle(
            // color: Colors.black87,
            letterSpacing: 1.0,
            fontSize: 34.0,
            fontFamily: GoogleFonts.abrilFatface().fontFamily,
          ),
        ),
        useMaterial3: true,
      ),
    );
  }
}
