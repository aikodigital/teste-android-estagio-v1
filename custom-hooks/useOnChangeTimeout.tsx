import { useRef } from 'react';

const useOnChangeTimeout = (time: number = 2000) => {
  const timeoutRef = useRef<NodeJS.Timeout>();
  const runTimeout = (fn: () => any) => {
    if (timeoutRef.current) clearTimeout(timeoutRef.current);
    timeoutRef.current = setTimeout(async () => {
      await fn();
    }, time);
  };

  return { runTimeout };
};

export default useOnChangeTimeout;
