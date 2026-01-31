Message Format  

| Offset (bytes) | Length | Hex Bytes                 | Field          | Description                 |
| -------------: | -----: | ------------------------- | -------------- | --------------------------- |
|            0–1 |      2 | `42 52`                   | Magic          | `"BR"`                      |
|              2 |      1 | `01`                      | Version        | Protocol version            |
|              3 |      1 | `03`                      | Type           | `PUBLISH`                   |
|            4–5 |      2 | `00 01`                   | Flags          | ACK required                |
|            6–7 |      2 | `00 14`                   | Header Length  | 20 bytes                    |
|           8–11 |      4 | `00 00 00 19`             | Frame Length   | 25 bytes (header + payload) |
|          12–19 |      8 | `00 00 00 00 00 00 00 2A` | Correlation ID | `42`                        |
|          20–24 |      5 | `68 65 6C 6C 6F`          | Payload        | UTF-8 `"hello"`             |

