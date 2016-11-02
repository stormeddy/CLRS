% a=[0 1 2 3 4 5 6 7]';
% fa=fft(a)
% rfa=recursive_fft(a)
% ifft(fa)
% recursive_ifft(rfa)
% 
% 
b=[1 2 3 4 5 6 7 8 9]';
fb=fft(b)
rfb=recursive_fft_3(b)
ifft(fb)
recursive_ifft_3(rfb)

% c=[0 2 3 -1 4 5 7 9]';
% fc=fft(c)
% rfc=iterative_fft(c)
