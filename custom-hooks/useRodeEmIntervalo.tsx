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
  }, []);
};

export default useRodeEmIntervalo;
