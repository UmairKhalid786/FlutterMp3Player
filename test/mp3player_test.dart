import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:mp3player/mp3player.dart';

void main() {
  const MethodChannel channel = MethodChannel('mp3player');

  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(await Mp3player.platformVersion, '42');
  });
}
