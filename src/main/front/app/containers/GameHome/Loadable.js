/**
 *
 * Asynchronously loads the component for GameHome
 *
 */

import loadable from 'utils/loadable';

export default loadable(() => import('./index'));
