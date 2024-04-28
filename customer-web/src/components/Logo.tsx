export default function Logo({ className }: { className: string }) {
  return (
    <svg
      className={className}
      viewBox="0 0 119 111"
      fill="none"
      xmlns="http://www.w3.org/2000/svg"
    >
      <g filter="url(#filter0_d_0_1)">
        <path
          d="M70.04 -9.53674e-07V1.12H68.6C64.84 1.12 59.72 4 59.72 12.8V64H44.6L15.4 9.36C15.4 9.36 15.4 48.08 15.32 48.8C15.32 57.76 18.92 62.96 24.84 62.96H27.08V64H4.68V62.96H7.24C10.36 62.96 14.28 57.68 14.28 48.32V8.8C14.28 3.68 10.12 1.04 7.24 1.04H5.88V-9.53674e-07H34.84L58.6 45.2V12.72C58.6 5.6 54.44 1.12 50.36 1.12H46.84V-9.53674e-07H70.04Z"
          fill="#292524"
        />
      </g>
      <g filter="url(#filter1_d_0_1)">
        <path
          d="M48.96 39V40.12H50.4C54.16 40.12 59.28 43 59.28 51.8V103H74.4L103.6 48.36C103.6 48.36 103.6 87.08 103.68 87.8C103.68 96.76 100.08 101.96 94.16 101.96H91.92V103H114.32V101.96H111.76C108.64 101.96 104.72 96.68 104.72 87.32V47.8C104.72 42.68 108.88 40.04 111.76 40.04H113.12V39H84.16L60.4 84.2V51.72C60.4 44.6 64.56 40.12 68.64 40.12H72.16V39H48.96Z"
          fill="#292524"
        />
      </g>
      <defs>
        <filter
          id="filter0_d_0_1"
          x="0.679932"
          y="0"
          width="73.3601"
          height="72"
          filterUnits="userSpaceOnUse"
          colorInterpolationFilters="sRGB"
        >
          <feFlood floodOpacity="0" result="BackgroundImageFix" />
          <feColorMatrix
            in="SourceAlpha"
            type="matrix"
            values="0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 127 0"
            result="hardAlpha"
          />
          <feOffset dy="4" />
          <feGaussianBlur stdDeviation="2" />
          <feComposite in2="hardAlpha" operator="out" />
          <feColorMatrix
            type="matrix"
            values="0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0.25 0"
          />
          <feBlend
            mode="normal"
            in2="BackgroundImageFix"
            result="effect1_dropShadow_0_1"
          />
          <feBlend
            mode="normal"
            in="SourceGraphic"
            in2="effect1_dropShadow_0_1"
            result="shape"
          />
        </filter>
        <filter
          id="filter1_d_0_1"
          x="44.96"
          y="39"
          width="73.3601"
          height="72"
          filterUnits="userSpaceOnUse"
          colorInterpolationFilters="sRGB"
        >
          <feFlood floodOpacity="0" result="BackgroundImageFix" />
          <feColorMatrix
            in="SourceAlpha"
            type="matrix"
            values="0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 127 0"
            result="hardAlpha"
          />
          <feOffset dy="4" />
          <feGaussianBlur stdDeviation="2" />
          <feComposite in2="hardAlpha" operator="out" />
          <feColorMatrix
            type="matrix"
            values="0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0.25 0"
          />
          <feBlend
            mode="normal"
            in2="BackgroundImageFix"
            result="effect1_dropShadow_0_1"
          />
          <feBlend
            mode="normal"
            in="SourceGraphic"
            in2="effect1_dropShadow_0_1"
            result="shape"
          />
        </filter>
      </defs>
    </svg>
  );
}
