# Java Run-Length Encoding

This sample demonstrates functions of finite streams that maintain stream-related state.

The `Encode` function takes an input stream of integers and emits an output stream
after applying [run-length encoding](https://en.wikipedia.org/wiki/Run-length_encoding).
Encoding or decoding an empty input stream results in an empty output stream.
For example, the stream:
```
< 0, 1, 1, 2, 2, 2 >

```
encodes to the following stream:
```
< 1, 0, 2, 1, 3, 2 >

```

The `Decode` function takes a run-length encoded input stream and emits a decoded output stream.
For example, the stream:
```
< 3, 1, 1, 0 >

```
decodes to the following stream:
```
< 1, 1, 1, 0 >

```

Of course, `Encode` followed by `Decode` produces the original stream of integers.

**Optional exercise:** when will `Decode` followed by `Encode` _not_ produce the original stream of integers? 
