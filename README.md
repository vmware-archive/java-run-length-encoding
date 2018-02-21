# Java Run-Length Encoding

This sample demonstrates functions of finite streams that maintain stream-related state.

The `Encode` function takes an input stream of integers and emits an output stream
after applying [run-length encoding](https://en.wikipedia.org/wiki/Run-length_encoding).
Encoding or decoding an empty input stream results in an empty output stream.
The `Decode` function takes a run-length encoded input stream and emits a decoded output stream.
`Encode` followed by `Decode` produces the original stream of integers. 
