import 'package:flutter_animate/flutter_animate.dart';
import 'package:riverpod_annotation/riverpod_annotation.dart';
import 'package:shared_preferences/shared_preferences.dart';

import '../models/auth.dart';

part 'auth_controller.g.dart';

const _dummyUser = Auth.signedIn(
    id: -1,
    displayName: 'Dummy Dum',
    email: 'dummy@dummy.com',
    token: 'dummy-token-secret');

@riverpod
class AuthController extends _$AuthController {
  late SharedPreferences _prefs;
  static const _sharedPrefsKey = 'token';

  @override
  Future<Auth> build() async {
    _prefs = await SharedPreferences.getInstance();
    _persistenceRefreshLogic();
    return _loginRecoveryAttempt();
  }

  Future<Auth> _loginRecoveryAttempt() {
    try {
      final savedToken = _prefs.getString(_sharedPrefsKey);
      if (savedToken == null) throw const UnauthorizedException('No token');
      return _loginWithToken(savedToken);
    } catch (_, __) {
      _prefs.remove(_sharedPrefsKey).ignore();
      return Future.value(const Auth.signedOut());
    }
  }

  Future<void> logout() async {
    await Future<void>.delayed(1.seconds);
    state = const AsyncData(Auth.signedOut());
  }

  Future<void> login(String email, String password) async {
    final result = await Future.delayed(1.seconds, () => _dummyUser);
    state = AsyncData(result);
  }

  Future<Auth> _loginWithToken(String token) async {
    final loginAttempt = await Future.delayed(1.seconds, () => true);
    if (loginAttempt) return _dummyUser;
    throw const UnauthorizedException('401 Unauthorized');
  }

  void _persistenceRefreshLogic() {
    ref.listenSelf((_, next) {
      if (next.isLoading) return;
      if (next.hasError) {
        _prefs.remove(_sharedPrefsKey);
        return;
      }

      next.requireValue.map<void>(
          signedIn: (signedIn) =>
              _prefs.setString(_sharedPrefsKey, signedIn.token),
          signedOut: (signedOut) {
            _prefs.remove(_sharedPrefsKey);
          });
    });
  }
}

class UnauthorizedException implements Exception {
  const UnauthorizedException(this.message);
  final String message;
}
