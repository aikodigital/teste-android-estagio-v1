import { useEffect, useRef } from 'react';

const useRodeEmIntervalo = (fn: () => Promise<any>, delay: number) => {
  const intervaloRef = useRef<NodeJS.Timeout>();

  useEffect(() => {
    const rodarIntervalo = async () => {
      await fn();
      if (!intervaloRef.current) {
        intervaloRef.current = setInterval(async () => await fn(), delay);
      }
    };
    rodarIntervalo();

    return () => {
      clearInterval(intervaloRef.current);
    };
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);
};

export default useRodeEmIntervalo;
