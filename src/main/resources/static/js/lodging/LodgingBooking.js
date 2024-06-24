document.addEventListener('DOMContentLoaded', function () {
    const reservationForm = document.getElementById('reservationForm'); // 예약 폼 요소 가져오기
    const selectedDate = document.getElementById('selectedDate');
    const total = document.getElementById('total');
    const reservation = document.getElementById('reservation'); // 예약 폼 요소 가져오기

    // 예약 폼에 날짜와 인원수 정보를 표시하는 함수
    function showReservationInfo() {
        // sessionStorage에서 데이터 가져오기
        const storedStartDate = sessionStorage.getItem('startDate');
        const storedEndDate = sessionStorage.getItem('endDate');
        const storedAdultCount = parseInt(sessionStorage.getItem('adultCount')) || 1;
        const storedChildCount = parseInt(sessionStorage.getItem('childCount')) || 0;

        // 날짜 정보 표시
        if (storedStartDate && storedEndDate) {
            const startDate = new Date(storedStartDate);
            const endDate = new Date(storedEndDate);
            const nights = Math.ceil((endDate - startDate) / (1000 * 60 * 60 * 24));
            selectedDate.innerHTML = `${startDate.getFullYear()}.${startDate.getMonth() + 1}.${startDate.getDate()} ~ ${endDate.getFullYear()}.${endDate.getMonth() + 1}.${endDate.getDate()}, ${nights}박`;
        }

        // 인원수 정보 표시
        total.innerHTML = `성인 ${storedAdultCount}명, 아동 ${storedChildCount}명`;
    }

    // 예약 폼 제출 시 처리
    // reservation.addEventListener('submit', function (event) {
    // });

    // 페이지 로드 시 예약 정보를 표시
    showReservationInfo();
});
