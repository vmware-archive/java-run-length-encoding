# Java Run-Length Encoding

This sample demonstrates functions of finite streams that maintain stream-related state.

The `Encode` function takes an input stream of integers and emits 
a [run-length encoding](https://en.wikipedia.org/wiki/Run-length_encoding)
output stream in which each maximal consecutive sequence of a given value is replaced
by the size of the sequence followed by the value.
For example, the stream:
```
< 0, 1, 1, 2, 2, 2 >

```
encodes to:
```
< 1, 0, 2, 1, 3, 2 >

```
Encoding or decoding an empty input stream results in an empty output stream.

The `Decode` function takes a run-length encoded input stream and emits the decoded output stream.
For example, the stream:
```
< 3, 1, 1, 0 >

```
decodes to:
```
< 1, 1, 1, 0 >

```

**Optional exercise:** of course `Encode` followed by `Decode` produces the original stream of integers, but when will
`Decode` followed by `Encode` _not_ produce the original stream of integers? ([Hint](decode/src/test/java/functions/DecodeTests.java)) 
