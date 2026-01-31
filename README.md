Message format

| Field          | Offset (bytes) | Size (bytes) | Type            | Description                                  |
|----------------|----------------|--------------|-----------------|----------------------------------------------|
| magic          | 0              | 2            | uint16          | Protocol magic (`0x4252` = "BR")             |
| version        | 2              | 1            | uint8           | Protocol version                             |
| type           | 3              | 1            | uint8           | Message type (`CONNECT`, `PUBLISH`, etc.)    |
| flags          | 4              | 2            | uint16          | Bit flags (ACK required, compression, etc.)  |
| headerLen      | 6              | 2            | uint16          | Total header length in bytes                 |
| frameLen       | 8              | 4            | uint32          | Total frame length (header + payload)        |
| correlationId  | 12             | 8            | uint64          | Correlation / trace identifier               |
| payload        | 20             | N            | byte[]          | Message payload                              |
| **TOTAL**      | —              | **20 + N**   | —               | Minimum frame size                           |
