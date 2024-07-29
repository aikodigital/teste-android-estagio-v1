import { Bus } from './types';

declare global {
  namespace ReactNavigation {
    interface RootParamList {
      Lines: undefined;
      Details: {
        bus: Bus;
        lineDetails: {
          lt0: string;
          lt1: string;
        };
      };
    }
  }
}
