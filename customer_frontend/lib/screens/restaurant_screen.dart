import 'package:flutter/material.dart';
import 'package:hooks_riverpod/hooks_riverpod.dart';

class RestaurantScreen extends ConsumerWidget {
  const RestaurantScreen({this.id, super.key});

  final String? id;

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    return Scaffold(
      extendBodyBehindAppBar: true,
      appBar: PreferredSize(
        preferredSize: const Size.fromHeight(64.0),
        child: Padding(
          padding: const EdgeInsets.symmetric(horizontal: 8.0),
          child: AppBar(
            leading: Container(
              decoration: BoxDecoration(
                color: Colors.white,
                border: Border.all(
                  color: Colors.black,
                  width: 1.0,
                ),
                borderRadius: const BorderRadius.all(
                  Radius.circular(
                    99.0,
                  ),
                ),
              ),
              child: IconButton(
                icon: const Icon(Icons.arrow_back_rounded),
                onPressed: () {
                  Navigator.pop(context);
                },
              ),
            ),
            // title: const Text('Restaurant'),
          ),
        ),
      ),
      body: const SingleChildScrollView(
        child: Column(
          children: [
            HeroBox(),
            Menu(),
          ],
        ),
      ),
    );
  }
}

class Menu extends StatelessWidget {
  const Menu({
    super.key,
  });

  @override
  Widget build(BuildContext context) {
    return Container(
      padding: const EdgeInsets.all(16.0),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          const SizedBox(height: 12.0),
          for (int i = 0; i < 20; i++)
            Padding(
              padding: const EdgeInsets.only(bottom: 20.0),
              child: Row(
                children: [
                  Expanded(
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        Text(
                          'Pizza margerita $i',
                          style: const TextStyle(
                            fontSize: 16.0,
                            color: Colors.black87,
                            fontWeight: FontWeight.w700,
                            fontFamily: 'Inter',
                          ),
                        ),
                        Text(
                          'Tomato sauce, mozzarella, basil, olive oil',
                          maxLines: 1,
                          style: const TextStyle(
                            fontSize: 14.0,
                            color: Colors.black87,
                            fontWeight: FontWeight.w400,
                            fontFamily: 'Inter',
                            overflow: TextOverflow.ellipsis,
                          ),
                        ),
                        Text(
                          '10.00 SEK',
                          style: const TextStyle(
                            fontSize: 14.0,
                            color: Colors.black87,
                            fontWeight: FontWeight.w400,
                            fontFamily: 'Inter',
                          ),
                        ),
                      ],
                    ),
                  ),
                  IconButton(
                    icon: const Icon(Icons.add_circle_outline_rounded),
                    onPressed: () {},
                  ),
                ],
              ),
            ),
        ],
      ),
    );
  }
}

class HeroBox extends StatelessWidget {
  const HeroBox({
    super.key,
  });

  @override
  Widget build(BuildContext context) {
    return SizedBox(
      height: 500.0,
      child: Container(
        decoration: const BoxDecoration(
          gradient: LinearGradient(
            begin: Alignment.topCenter,
            end: Alignment.bottomCenter,
            colors: [
              Color.fromRGBO(0, 0, 0, 0),
              Color.fromRGBO(205, 205, 205, 1),
            ],
          ),
          image: DecorationImage(
            image: NetworkImage(
                'https://plus.unsplash.com/premium_photo-1675451537771-0dd5b06b3985?q=80&w=3387&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D'),
            fit: BoxFit.cover,
          ),
        ),
        child: Container(
          decoration: const BoxDecoration(
            gradient: LinearGradient(
              begin: Alignment.topCenter,
              end: Alignment.bottomCenter,
              colors: [
                Color.fromRGBO(0, 0, 0, 0.0),
                Color.fromRGBO(0, 0, 0, 0.8),
              ],
            ),
          ),
          child: Padding(
            padding: const EdgeInsets.symmetric(horizontal: 16.0),
            child: Column(
              mainAxisAlignment: MainAxisAlignment.end,
              children: [
                Column(
                  children: [
                    Row(
                      children: [
                        Text(
                          'Restaurant Name',
                          style: Theme.of(context).textTheme.titleMedium,
                          textAlign: TextAlign.left,
                        ),
                      ],
                    ),
                    const Divider(
                      color: Colors.white,
                      thickness: 2.0,
                    ),
                    Text(
                      "Experience the true taste of Italy in this humble corner restaurant created by the legendary Matteo Ia ",
                      style: Theme.of(context).textTheme.bodyMedium,
                    ),
                    SizedBox(height: 8.0),
                    Row(
                      children: [
                        Row(
                          children: [
                            Container(
                              decoration: BoxDecoration(
                                color: Colors.transparent,
                                border: Border.all(
                                  color: Colors.white,
                                  width: 1.0,
                                ),
                                borderRadius: const BorderRadius.all(
                                  Radius.circular(
                                    8.0,
                                  ),
                                ),
                              ),
                              child: Text(
                                "Local favorite",
                                style: Theme.of(context).textTheme.bodyMedium,
                              ),
                              padding: const EdgeInsets.symmetric(
                                horizontal: 8.0,
                                vertical: 4.0,
                              ),
                            ),
                          ],
                        ),
                        Expanded(
                          child: Row(
                            mainAxisAlignment: MainAxisAlignment.end,
                            children: [
                              const Icon(Icons.star_rounded,
                                  color: Colors.white, size: 16.0),
                              const Icon(Icons.star_rounded,
                                  color: Colors.white, size: 16.0),
                              const Icon(Icons.star_rounded,
                                  color: Colors.white, size: 16.0),
                              const Icon(Icons.star_rounded,
                                  color: Colors.white, size: 16.0),
                              const Icon(Icons.star_half_rounded,
                                  color: Colors.white, size: 16.0),
                              const SizedBox(width: 8.0),
                              Text(
                                '4.5',
                                style: Theme.of(context).textTheme.bodyMedium,
                              ),
                            ],
                          ),
                        ),
                      ],
                    ),
                    SizedBox(height: 16.0),
                  ],
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }
}
