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
          surfaceTintColor: Colors.transparent,
          secondaryLabelStyle: TextStyle(
            color: Colors.white,
          ),
          shape: RoundedRectangleBorder(
            side: const BorderSide(color: Colors.black87, width: 1.4),
            borderRadius: BorderRadius.circular(8),
          ),
          labelStyle: const TextStyle(
            color: Colors.black,
          ),
          showCheckmark: false,
          elevation: 1.0,
        ),
        searchViewTheme: SearchViewThemeData(
          // surfaceTintColor: backgroundColor,
          // backgroundColor: backgroundColor,
          shape: RoundedRectangleBorder(
            side: const BorderSide(color: Colors.black87, width: 1.6),
            borderRadius: BorderRadius.circular(24),
          ),
          // backgroundColor: Colors.white,
          surfaceTintColor: Colors.transparent,
          dividerColor: Colors.transparent,
          elevation: 1.0,
        ),
        searchBarTheme: SearchBarThemeData(
          constraints: const BoxConstraints(
            minHeight: 46,
          ),
          textStyle: MaterialStatePropertyAll(
            TextStyle(
              height: 1.0,
              fontFamily: GoogleFonts.inter().fontFamily,
              fontSize: 16.0,
            ),
          ),
          surfaceTintColor: MaterialStatePropertyAll(Colors.white),
          elevation: MaterialStatePropertyAll(0.0),
        ),
        navigationBarTheme: const NavigationBarThemeData(
          // backgroundColor: Colors.transparent,
          surfaceTintColor: Colors.transparent,
          overlayColor: MaterialStatePropertyAll(Colors.transparent),
          indicatorColor: Colors.transparent,
          elevation: 1.0,
        ),
        appBarTheme: const AppBarTheme(
          elevation: 0.0,
          systemOverlayStyle: SystemUiOverlayStyle.dark,
          backgroundColor: Colors.transparent,
          surfaceTintColor: Colors.transparent,
          centerTitle: true,
          // iconTheme: IconThemeData(color: Colors.black87),
          // actionsIconTheme: IconThemeData(color: Colors.black87),
        ),
        // listTileTheme: const ListTileThemeData(
        //   subtitleTextStyle: TextStyle(
        //     color: Colors.black87,
        //     fontSize: 12.0,
        //   ),
        //
        //   // contentPadding: EdgeInsets.symmetric(horizontal: 16.0),
        // ),
        textTheme: TextTheme(
          bodySmall: TextStyle(
            color: Colors.black87,
            fontSize: 12.0,
            fontFamily: GoogleFonts.inter().fontFamily,
          ),
          bodyMedium: TextStyle(
            color: Colors.white,
            fontSize: 14.0,
            fontFamily: GoogleFonts.inter().fontFamily,
          ),
          titleSmall: TextStyle(
            letterSpacing: 1.0,
            fontSize: 24.0,
            fontFamily: GoogleFonts.abrilFatface().fontFamily,
          ),
          titleMedium: TextStyle(
            letterSpacing: 1.0,
            fontSize: 30.0,
            fontFamily: GoogleFonts.abrilFatface().fontFamily,
            color: Colors.white,
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
