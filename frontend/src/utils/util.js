// "1"로 들어온 숫자를 "01"로 바꾸기 위한 함수.
export function num2str(num) {
    let number = Number(num);
    if (number < 10 && number >= 1) {
        return "0"+number;
    } else {
        return number;
    }
}