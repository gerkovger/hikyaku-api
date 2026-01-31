Message Format

42 52        // magic "BR"
01           // version
03           // type = PUBLISH
00 01        // flags
00 14        // header length (20)
00 00 00 19  // frame length (25)
00 00 00 00 00 00 00 2A  // correlationId
68 65 6C 6C 6F           // "hello"
