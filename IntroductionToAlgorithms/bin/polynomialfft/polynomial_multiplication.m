function y=polynomial_multiplication(a,b)
    fa=recursive_fft(a);
    fb=recursive_fft(b);
    fy=fa.*fb;
    y=recursive_ifft(fy);
end