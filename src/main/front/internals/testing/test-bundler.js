// needed for regenerator-runtime
// (ES7 generator support is required by redux-saga)
import '@babel/polyfill';
import React from 'react';
React.useLayoutEffect = React.useEffect;
