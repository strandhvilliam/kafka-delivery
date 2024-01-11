import 'package:flutter/material.dart';
import 'package:hooks_riverpod/hooks_riverpod.dart';

class FindScreen extends ConsumerWidget {
  const FindScreen({super.key});

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    return Scaffold(
      extendBodyBehindAppBar: true,
      appBar: AppBar(
        title: SearchAnchor(
            isFullScreen: false,
            builder: (BuildContext context, SearchController controller) {
              return Padding(
                padding: const EdgeInsets.only(bottom: 8.0),
                child: SearchBar(
                  hintText: 'Search for food or restaurant',
                  controller: controller,
                  padding: const MaterialStatePropertyAll<EdgeInsets>(
                    EdgeInsets.symmetric(horizontal: 16.0),
                  ),
                  onTap: () {
                    controller.openView();
                  },
                  onChanged: (_) {
                    controller.openView();
                  },
                  leading: const Padding(
                    padding: EdgeInsets.only(top: 1.0, left: 2.0),
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
            }),
      ),
      body: Column(
        children: [
          Expanded(
            child: ListView(
              children: [
                HorizontalCategoryBar(),
                const SizedBox(height: 8.0),
                for (int i = 0; i < 20; i++)
                  Column(
                    children: [
                      Padding(
                        padding: const EdgeInsets.only(left: 16.0, right: 16.0),
                        child: SizedBox(
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
                      ),
                      ListTile(
                        title: Text('Item $i',
                            style: Theme.of(context).textTheme.titleSmall),
                        subtitle: Text('Subtitle $i'),
                        trailing: const Icon(Icons.chevron_right_rounded),
                        onTap: () {},
                      ),
                    ],
                  ),
              ],
            ),
          )
        ],
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
