import 'package:customer_frontend/router/routes.dart';
import 'package:flutter/material.dart';
import 'package:hooks_riverpod/hooks_riverpod.dart';

class FindScreen extends ConsumerWidget {
  const FindScreen({super.key});

  void navigateToRestaurant(BuildContext context, String id) {
    RestaurantRoute(id: "123").go(context);
  }

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    return Scaffold(
      extendBodyBehindAppBar: true,
      appBar: AppBar(
        // systemOverlayStyle: SystemUiOverlayStyle(Colors.white),
        title: const NomnomSearchBar(),
      ),
      body: Column(
        children: [
          Expanded(
            child: ListView(
              children: [
                const HorizontalCategoryBar(),
                const SizedBox(height: 8.0),
                for (int i = 0; i < 20; i++)
                  GestureDetector(
                    onTap: () => navigateToRestaurant(context, '$i'),
                    child: Padding(
                      padding: const EdgeInsets.all(16.0),
                      child: Column(
                        children: [
                          SizedBox(
                            height: 200.0,
                            child: Container(
                              decoration: const BoxDecoration(
                                borderRadius:
                                    BorderRadius.all(Radius.circular(16.0)),
                                image: DecorationImage(
                                  image: NetworkImage(
                                      'https://plus.unsplash.com/premium_photo-1675451537771-0dd5b06b3985?q=80&w=3387&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D'),
                                  fit: BoxFit.cover,
                                ),
                              ),
                              child: null,
                            ),
                          ),
                          ListTile(
                            contentPadding: const EdgeInsets.symmetric(
                              horizontal: 0.0,
                              vertical: 0.0,
                            ),
                            shape: const Border(
                              bottom: BorderSide(color: Colors.black, width: 1),
                            ),
                            title: Padding(
                              padding: const EdgeInsets.only(bottom: 8.0),
                              child: Text('Item and one $i',
                                  style:
                                      Theme.of(context).textTheme.titleSmall),
                            ),
                            subtitle: Padding(
                              padding: const EdgeInsets.only(bottom: 8.0),
                              child: Text(
                                maxLines: 2,
                                overflow: TextOverflow.ellipsis,
                                'Experience the true taste of Italy in this humble corner restaurant created by the legendary Matteo Ia ',
                                style: Theme.of(context).textTheme.bodySmall,
                              ),
                            ),
                            horizontalTitleGap: 12.0,
                            trailing: const FavoriteIconButton(),
                          ),
                          // Padding(
                          //   padding: const EdgeInsets.symmetric(horizontal: 16.0),
                          //   child: const Divider(thickness: 1.6),
                          // ),
                        ],
                      ),
                    ),
                  ),
              ],
            ),
          )
        ],
      ),
    );
  }
}

class NomnomSearchBar extends StatelessWidget {
  const NomnomSearchBar({
    super.key,
  });

  @override
  Widget build(BuildContext context) {
    return SearchAnchor(
        isFullScreen: false,
        viewConstraints: const BoxConstraints(
          minHeight: 46,
        ),
        builder: (BuildContext context, SearchController controller) {
          return Padding(
            padding: const EdgeInsets.only(bottom: 8.0),
            child: SearchBar(
              hintText: 'Search for food or restaurant',
              controller: controller,
              shape: const MaterialStatePropertyAll(
                StadiumBorder(
                  side: const BorderSide(color: Colors.black87, width: 1.6),
                ),
              ),
              onTap: () {
                controller.openView();
              },
              onChanged: (_) {
                controller.openView();
              },
              leading: const Padding(
                padding: EdgeInsets.only(top: 1.0, left: 8.0),
                child: Icon(
                  Icons.search_rounded,
                ),
              ),
            ),
          );
        },
        suggestionsBuilder:
            (BuildContext context, SearchController controller) {
          return List<ListTile>.generate(5, (int index) {
            final String item = 'item $index';
            return ListTile(
              title: Text(item),
              onTap: () {
                controller.closeView(item);
              },
            );
          });
        });
  }
}

class FavoriteIconButton extends StatelessWidget {
  const FavoriteIconButton({
    super.key,
  });

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.only(right: 12.0),
      child: IconButton(
        style: const ButtonStyle(
          shape: MaterialStatePropertyAll(
            CircleBorder(
              side: BorderSide(width: 1.4, color: Colors.black87),
            ),
          ),
        ),
        icon: const Padding(
          padding: const EdgeInsets.all(4.0),
          child: const Icon(Icons.favorite_outline_rounded, size: 24.0),
        ),
        onPressed: () {},
      ),
    );
  }
}

class HorizontalCategoryBar extends StatelessWidget {
  const HorizontalCategoryBar({
    super.key,
  });

  @override
  Widget build(BuildContext context) {
    return SizedBox(
      height: 50.0,
      child: ListView(
        padding: const EdgeInsets.only(left: 16.0, right: 16.0),
        scrollDirection: Axis.horizontal,
        children: const <Widget>[
          ChoiceChip(
            label: Text("Popular"),
            selected: false,
          ),
          SizedBox(width: 8.0),
          ChoiceChip(
            label: Text("Hamburgers"),
            selected: true,
          ),
          SizedBox(width: 8.0),
          ChoiceChip(
            label: Text("Pizza"),
            selected: false,
          ),
          SizedBox(width: 8.0),
          ChoiceChip(
            label: Text("Sushi"),
            selected: false,
          ),
          SizedBox(width: 8.0),
          ChoiceChip(
            label: Text("A la carte"),
            selected: false,
          ),
        ],
      ),
    );
  }
}
